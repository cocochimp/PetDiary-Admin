import { log, error, time, timeEnd } from './utils.js';
/*
管理所有事件处理函数，并生成一个代理函数，允许用户替换其中的某些实现
受制于小程序的运行时机制，在 locked 之后，不允许变更 handlerNames
*/
var HandlersController = /** @class */ (function () {
    function HandlersController(elementTag) {
        if (elementTag === void 0) { elementTag = ''; }
        this.elementTag = ''; // 主要是 debug 使用
        this.locked = false; // 是否已经锁定，锁定之后不允许新增 handler
        this.executionInProgress = false;
        this.executionQueue = [];
        this.handlerNames = [];
        this.handlerImpl = {};
        this.elementTag = elementTag;
    }
    HandlersController.prototype.addHandler = function (name, bindContext, impl, disableMultiImpl) {
        var _this = this;
        if (this.locked && this.handlerNames.indexOf(name) < 0) {
            throw new Error("\u4E0D\u5141\u8BB8\u52A8\u6001\u65B0\u589E handler: ".concat(name));
        }
        if (impl && !bindContext) {
            throw new Error("bindContext \u4E0D\u80FD\u4E3A\u7A7A\uFF0C\u8FD0\u884C\u65F6\u9700\u8981\u7528\u5230\u5B83");
        }
        if (bindContext && !impl) {
            throw new Error('missing impl');
        }
        if (typeof name !== 'string') {
            throw new Error("name \u7684\u7C7B\u578B\u4E0D\u5408\u6CD5\uFF08".concat(typeof name, "\uFF09\uFF0C\u5B83\u5E94\u8BE5\u662F\u4E00\u4E2A\u5B57\u7B26\u4E32"));
        }
        else if (typeof impl !== 'function' && typeof impl !== 'undefined') {
            throw new Error("impl \u7684\u7C7B\u578B\u4E0D\u5408\u6CD5\uFF08".concat(typeof impl, "\uFF09\uFF0C\u5B83\u5E94\u8BE5\u662F\u4E00\u4E2A\u51FD\u6570"));
        }
        if (this.handlerNames.indexOf(name) < 0) {
            this.handlerNames.push(name);
        }
        this.handlerImpl[name] = this.handlerImpl[name] || [];
        var self = this;
        if (!impl) {
            return function () { };
        }
        if (disableMultiImpl &&
            this.getHandlersByName(name, bindContext).length > 0) {
            throw new Error("".concat(name, " \u5DF2\u7ECF\u6CE8\u518C\u4E86\u4E00\u4E2A\u5B9E\u73B0\uFF0C\u4E0D\u80FD\u518D\u65B0\u589E"));
        }
        var implInfo = {
            // 裹一层，把 off 方法收集下来
            fn: function () {
                var args = [];
                for (var _i = 0; _i < arguments.length; _i++) {
                    args[_i] = arguments[_i];
                }
                if (!this)
                    throw new Error('missing context, cannot find matching handler');
                var ctx = self.filterContext(bindContext, this);
                // context 不匹配，不处理
                if (!ctx)
                    throw new Error('handler context mismatch, refuse executing');
                try {
                    var ret = impl.apply(this, args);
                    if (typeof ret === 'function') {
                        implInfo.off = ret; // 这个 off 其实已经不用了
                    }
                    else {
                        return ret; // 如果不是 function，认为这个返回值有用，不是 off
                    }
                }
                catch (e) {
                    error("".concat(self.elementTag, " ").concat(name, " \u6267\u884C\u51FA\u9519\uFF0C\u9519\u8BEF\u4FE1\u606F\uFF1A").concat(e.message));
                    throw e;
                }
            },
            bindContext: bindContext,
        };
        this.handlerImpl[name].push(implInfo);
        return function () {
            log("off ".concat(name, " - ").concat(_this.elementTag));
            var handlers = _this.handlerImpl[name]; // 这里闭包里有明确的 implInfo, 不需要根据 context 过滤
            if (!handlers) {
                var msg = "\u6CA1\u6709\u627E\u5230 ".concat(name, " \u5BF9\u5E94\u7684\u5B9E\u73B0\uFF0C\u662F\u5426\u5DF2\u7ECF\u88AB reset \u4E86 \uFF1F\u8BF7\u68C0\u67E5\u751F\u547D\u5468\u671F\u6D41\u7A0B");
                error(msg);
                throw new Error(msg);
            }
            var index = handlers.indexOf(implInfo);
            var offFunction = handlers[index].off;
            if (offFunction) {
                try {
                    offFunction.call(undefined);
                }
                catch (e) {
                    error("\u6267\u884C ".concat(name, " \u7684\u53CD\u6CE8\u518C\u65B9\u6CD5\u65F6\u51FA\u9519\uFF1A"));
                    error(e);
                    throw e;
                }
            }
            if (index >= 0) {
                handlers.splice(index, 1);
            }
        };
    };
    HandlersController.prototype.lockHandlerNames = function () {
        this.locked = true;
    };
    HandlersController.prototype.resetAllImpl = function () {
        this.handlerImpl = {};
    };
    HandlersController.prototype.getHandlersByName = function (name, context) {
        var _this = this;
        if (!context)
            throw new Error('context is required');
        return this.handlerImpl[name].filter(function (handler) {
            return _this.filterContext(handler.bindContext, context);
        });
    };
    HandlersController.prototype.callHandlers = function (name, context, args) {
        var controller = this;
        var handlers = (controller.handlerImpl[name] || []).filter(function (handler) {
            var targetCtx = controller.filterContext(handler === null || handler === void 0 ? void 0 : handler.bindContext, context);
            return !!targetCtx;
        }); // 过滤并复制出来，防止执行过程中有人往里面塞东西造成乱序
        var callId = "".concat(controller.elementTag, "-handlerCalled-").concat(name, "-").concat(Math.floor(Math.random() * 1000));
        var tipText = "".concat(callId, " - handlers count ").concat(handlers.length);
        log(Date.now(), tipText);
        var returnValue;
        for (var i = 0; i < handlers.length; i++) {
            log(Date.now(), "".concat(tipText, " - executing ").concat(i + 1, "/").concat(handlers.length));
            time(callId);
            var handler = handlers[i];
            var ret = handler.fn.apply(context, args);
            if (i === 0) {
                // 只返回第一个
                returnValue = ret;
            }
            timeEnd(callId);
        }
        return returnValue;
    };
    // 返回给 appx 的可能是一个 Promise，无论 appx 等不等，这里都会串行执行
    HandlersController.prototype.getHandlersImplProxy = function () {
        var _this = this;
        if (!this.locked) {
            throw new Error('please lock the handler names first');
        }
        var handlers = this.handlerNames.reduce(function (acc, handlerName) {
            var controller = _this;
            //@ts-expect-error
            acc[handlerName] = function () {
                var args = [];
                for (var _i = 0; _i < arguments.length; _i++) {
                    args[_i] = arguments[_i];
                }
                // 这里要保留调用方的 this
                var appxCaller = this;
                if (!appxCaller) {
                    console.warn('cannot find appx caller instance');
                }
                // deriveData 的时候，异步可能会有问题，直接改同步看看疗效
                var err;
                var ret;
                try {
                    ret = controller.callHandlers(handlerName, appxCaller, args);
                }
                catch (e) {
                    err = e;
                }
                // await unlock();
                // 防止后续所有事情被死锁
                if (err)
                    throw err;
                // setTimeout(() => {
                // unlock();
                // }, 0);
                return ret;
                // return Promise.resolve(
                //   (async () => {
                //   })(),
                // );
            };
            return acc;
        }, {});
        return handlers;
    };
    //@ts-expect-error
    HandlersController.prototype.filterContext = function (bindContext, nowContext) {
        if (typeof bindContext === 'function') {
            if (!bindContext(nowContext)) {
                return false;
            }
        }
        else if (bindContext !== nowContext) {
            return false;
        }
        return nowContext;
    };
    return HandlersController;
}());
export default HandlersController;
