import { act, mountElement, serverRender, createElement } from './r.js';
import { log as globalLog, error as globalError, EComponent2Status, getComponent2Status, updateComponent2Status, getIdFromAppxInstance, instanceKeyPropNames, shallowCompare, } from './utils.js';
import HandlersController from './handlers.js';
import { reactContext, useSyncMiniData } from './hooks.js';
import { ETargetPlatform } from './types.js';
import { platformConfig } from './platform.js';
var FUNCTIONAL_MINI_PAGE_DOM_PLACEHOLDER = 'MINIFISH_PAGE_DOM_PLACEHOLDER';
var DANGER_ZONE_BYPASS_FUNCTION_CALL_WITH_DATA = 'DANGER_ZONE_BYPASS_FUNCTION_CALL_WITH_DATA'; // 绕过所有函数方法，直接返回 data。不要在生产环境使用！
function compositeElementWithContext(
//@ts-expect-error
id, 
//@ts-expect-error
elementFn, appxContext, 
//@ts-expect-error
pendingProps) {
    var el = createElement(elementFn, pendingProps);
    // eslint-disable-next-line react/no-children-prop
    return createElement(reactContext.Provider, { value: appxContext, key: id, children: [el] }, el);
}
export function flushReactTree(elementMap) {
    var children = [];
    for (var id in elementMap) {
        if (!Object.prototype.hasOwnProperty.call(elementMap, id))
            continue;
        var item = elementMap[id];
        if (item.unmounted) {
            continue;
        }
        if (item.elementInstance && !item.pendingProps) {
            children.push(item.elementInstance);
            continue;
        }
        var contextElement = compositeElementWithContext(id, item.elementFn, item.appxContext, item.pendingProps);
        children.push(contextElement);
        item.elementInstance = contextElement;
        item.pendingProps = null;
    }
    var parent = createElement('div', {}, children);
    return parent;
}
export function functionalMiniElement(element, displayName /* 用于问题排查，和小程序 axml 无关 */, elementType, defaultProps, _targetPlatform, elementOption) {
    var _a;
    if (displayName === void 0) { displayName = ''; }
    if (elementOption === void 0) { elementOption = {}; }
    var targetPlatform = _targetPlatform !== null && _targetPlatform !== void 0 ? _targetPlatform : ETargetPlatform.alipay;
    // 配置客户端环境
    var _b = platformConfig[targetPlatform], pageEvents = _b.pageEvents, componentEvents = _b.componentEvents, buildOptions = _b.buildOptions, componentLifeCycleToMount = _b.componentLifeCycleToMount, componentLifeCycleToUnmount = _b.componentLifeCycleToUnmount, pageLifeCycleToMount = _b.pageLifeCycleToMount, pageLifeCycleToUnmount = _b.pageLifeCycleToUnmount, getPropsFromInstance = _b.getPropsFromInstance, componentPageEvents = _b.componentPageEvents;
    displayName = displayName || element.name;
    if (!displayName) {
        console.warn('为了方便问题排查，请传入组件的 displayName 参数，或不要使用匿名函数组件 https://medium.com/@stevemao/do-not-use-anonymous-functions-to-construct-react-functional-components-c5408ec8f4c7');
    }
    var nameTag = "[".concat(elementType, "/").concat(displayName || '(unnamed)', "]");
    //@ts-expect-error
    var log = function () {
        var args = [];
        for (var _i = 0; _i < arguments.length; _i++) {
            args[_i] = arguments[_i];
        }
        globalLog.apply(null, [nameTag].concat(args));
    };
    var logErrorAndThrow = function (err) {
        if (!err)
            return;
        err.message = "".concat(nameTag, " ").concat(err.message);
        globalError(err);
        throw err;
    };
    if (elementType === 'component') {
        if (getComponent2Status() === EComponent2Status.UNKNOWN) {
            updateComponent2Status();
        }
        if (getComponent2Status() === EComponent2Status.INVALID) {
            // 如果还是 Unknown，就先不管了
            throw new Error("\u65E0\u6CD5\u6CE8\u518C ".concat(nameTag, " \u7EC4\u4EF6\uFF0C\u56E0\u4E3A\u5F53\u524D\u5C0F\u7A0B\u5E8F\u73AF\u5883\u672A\u5F00\u542F component2\u3002\u914D\u7F6E\u5165\u53E3\u5728 mini.project.json : { \"compileOptions\": { \"component2\": true } }\uFF0C\u6216\u5728 IDE \u8BE6\u60C5>\u9879\u76EE\u914D\u7F6E\u4E2D\u52FE\u9009"));
        }
    }
    var platformExposedEvents = elementType === 'page' ? pageEvents : componentEvents;
    var defaultPropKeys = Object.keys(defaultProps || {});
    var observers = {};
    var elementMap = {};
    //@ts-expect-error
    var commonTestRenderer;
    function updateReactTree() {
        act(function () {
            var parent = flushReactTree(elementMap);
            //@ts-expect-error
            if (!commonTestRenderer) {
                commonTestRenderer = mountElement(parent);
            }
            else {
                commonTestRenderer.update(parent);
            }
        });
    }
    var WrappedElementFn = function (props) {
        var miniData;
        if (props && props[DANGER_ZONE_BYPASS_FUNCTION_CALL_WITH_DATA]) {
            miniData = props[DANGER_ZONE_BYPASS_FUNCTION_CALL_WITH_DATA];
            if (typeof miniData === 'string' && miniData.indexOf('%7B') === 0) {
                miniData = JSON.parse(decodeURIComponent(miniData));
            }
        }
        else {
            try {
                miniData = element.call(undefined, props);
            }
            catch (e) {
                //@ts-expect-error
                e.message = "\u6E32\u67D3\u51FA\u9519 ".concat(e.message);
                return logErrorAndThrow(e);
            }
        }
        if (typeof miniData === 'undefined') {
            log('函数组件没有返回渲染数据，请检查代码逻辑');
            miniData = {};
        }
        else if (typeof miniData !== 'object') {
            var e = new Error("\u51FD\u6570\u7EC4\u4EF6\u8FD4\u56DE\u7684\u6E32\u67D3\u6570\u636E\u4E0D\u5408\u6CD5\uFF0C\u6536\u5230\u7684\u7C7B\u578B\u4E3A ".concat(typeof miniData));
            return logErrorAndThrow(e);
        }
        // 不允许和 props 里的 key 重复
        var conflictKeys = [];
        var dataKeys = Object.keys(miniData) || [];
        for (var key in props) {
            if (dataKeys.indexOf(key) >= 0) {
                conflictKeys.push(key);
            }
        }
        if (conflictKeys.length > 0) {
            var e = new Error("\u5C0F\u7A0B\u5E8F\u81EA\u5B9A\u4E49\u7EC4\u4EF6\u8FD4\u56DE\u7684\u6E32\u67D3\u6570\u636E\u548C props \u91CC\u7684 key \u91CD\u590D\uFF1A".concat(conflictKeys.join(', ')));
            return logErrorAndThrow(e);
        }
        // 上面的 early-return 都是检查和抛错，忽略 hooks 规则
        useSyncMiniData(miniData || {});
        return createElement('div', {}, FUNCTIONAL_MINI_PAGE_DOM_PLACEHOLDER);
    };
    // 在 onload 的时候，正式创建一个 react 组件
    function hookLoadToMountReactComponent() {
        var args = [];
        for (var _i = 0; _i < arguments.length; _i++) {
            args[_i] = arguments[_i];
        }
        var appxInstance = this;
        var id = getIdFromAppxInstance(appxInstance);
        if (elementMap[id])
            throw new Error("duplicate id of appx instance, this might be a bug of minifish hooks. id: ".concat(id));
        var context = generateInstanceContext(appxInstance, false);
        appxInstance[instanceKeyPropNames] = defaultPropKeys;
        log('will mount react component');
        var initProps = {};
        if (elementType === 'component') {
            initProps = getPropsFromInstance(appxInstance, defaultPropKeys);
        }
        if (elementType === 'page') {
            initProps = {
                query: args[0] || {},
            };
        }
        elementMap[id] = {
            appxContext: context,
            appxId: id,
            elementFn: WrappedElementFn,
            pendingProps: initProps,
            unmounted: false,
            propKeys: defaultPropKeys,
        };
        updateReactTree();
        // 触发 onLoad
        if (elementType === 'page') {
            log('页面（Page）已经 Mount，开始触发 onLoad');
            return context.handlersController.callHandlers(pageLifeCycleToMount, appxInstance, args); // 直接调用controller 内部的方法，插队执行 onLoad
        }
        else {
            log("\u7EC4\u4EF6\uFF08Component\uFF09\u5DF2\u7ECF Mount\uFF0C\u5F00\u59CB\u89E6\u53D1 ".concat(componentLifeCycleToMount));
            return context.handlersController.callHandlers(componentLifeCycleToMount, appxInstance, args);
        }
    }
    //@ts-expect-error
    function dispatchNewProps(appxInstance, nextProps) {
        var id = getIdFromAppxInstance(appxInstance);
        var instance = elementMap[id];
        if (instance) {
            instance.pendingProps = nextProps;
            updateReactTree();
        }
    }
    function hookUnloadToUnmount() {
        var appxInstance = this;
        var id = getIdFromAppxInstance(appxInstance);
        log("will unmount react element of ".concat(id));
        if (!elementMap[id]) {
            log("\u627E\u4E0D\u5230 id \u4E3A ".concat(id, " \u7684\u5B9E\u4F8B\uFF0C\u8BF7\u68C0\u67E5\u662F\u5426\u5F00\u542F\u4E86 component2 \u3002\u8FD9\u4E5F\u53EF\u80FD\u662F Minifish \u7684 bug\u3002"));
        }
        elementMap[id].unmounted = true;
        updateReactTree();
        // TODO: 清理 appx context
    }
    var handlersController = new HandlersController(nameTag);
    //@ts-expect-error
    var anyUnknownContext = function (ctx) {
        if (!ctx)
            throw new Error('ctx is required');
        var id = getIdFromAppxInstance(ctx);
        return !elementMap[id];
    };
    var anyContext = function () { return true; };
    if (elementType === 'page') {
        handlersController.addHandler(pageLifeCycleToMount, anyUnknownContext, hookLoadToMountReactComponent);
        handlersController.addHandler(pageLifeCycleToUnmount, anyContext, hookUnloadToUnmount);
    }
    else {
        // 组件的关键生命周期
        handlersController.addHandler(componentLifeCycleToMount, anyUnknownContext, hookLoadToMountReactComponent);
        handlersController.addHandler(componentLifeCycleToUnmount, anyContext, hookUnloadToUnmount);
        // 把 props 变更分发到对应的组件里
        if (targetPlatform === ETargetPlatform.alipay) {
            handlersController.addHandler('deriveDataFromProps', anyContext, function hookDeriveDataFromProps(nextProps) {
                if (this.props && this.props === nextProps) {
                    // 可能是setData触发的，不要死循环了
                    return;
                }
                else {
                    if (shallowCompare(this.props, nextProps, ['$slots'])) {
                        return;
                    }
                }
                return dispatchNewProps(this, nextProps);
            });
        }
        else if (targetPlatform === ETargetPlatform.wechat) {
            var props_1 = defaultPropKeys.join(', ');
            log("\u5C06\u6CE8\u518C\u4EE5\u4E0B key \u7684 props \u66F4\u65B0\uFF1A".concat(props_1));
            observers = Object.assign((_a = {},
                // 忽略函数参数，直接从 this 里面找
                // 搞不懂小程序的设计逻辑，为什么非要在 props 里夹着 data
                _a[props_1] = function () {
                    var args = [];
                    for (var _i = 0; _i < arguments.length; _i++) {
                        args[_i] = arguments[_i];
                    }
                    log('observer is being called', args);
                    var newProps = getPropsFromInstance(this, defaultPropKeys);
                    return dispatchNewProps(this, newProps);
                    //
                },
                _a), observers);
        }
    }
    // 做一次预渲染，获取所有 appx 需要的属性
    var generateInstanceContext = function (
    //@ts-expect-error
    instance, ifServerRender) {
        return {
            instance: instance,
            handlersController: handlersController,
            ifServerRender: ifServerRender,
            debugLog: log,
            platformConfig: platformConfig[targetPlatform],
        };
    };
    // 收集 initData
    var initData = {};
    var fakeAppxInstance = {
        $id: "_minifish_hooks_pre_render_".concat(Math.random()),
        //@ts-expect-error
        setData: function (data) {
            initData = Object.assign({}, initData || {}, data);
        },
    };
    var fakeCtx = generateInstanceContext(fakeAppxInstance, true);
    var serverEl = compositeElementWithContext(fakeAppxInstance.$id, WrappedElementFn, fakeCtx, defaultProps || {});
    serverRender(serverEl);
    log('serverRendered with initData', initData);
    // 拼装对象，喂给 appx。产出的配置应该只能喂一次，喂多了 appx 不认。
    handlersController.lockHandlerNames();
    // 把生命周期和一般事件处理分开归类，因为组件的事件处理要多裹一层
    var lifeCycleHandlers = {};
    var userEventHandlers = {};
    var componentPageEventsHandlers = {};
    var handlers = handlersController.getHandlersImplProxy();
    for (var name_1 in handlers) {
        if (platformExposedEvents.indexOf(name_1) >= 0) {
            //@ts-expect-error
            lifeCycleHandlers[name_1] = handlers[name_1];
        }
        else if (componentPageEvents.indexOf(name_1) >= 0) {
            //@ts-expect-error
            componentPageEventsHandlers[name_1] = handlers[name_1];
        }
        else {
            //@ts-expect-error
            userEventHandlers[name_1] = handlers[name_1];
        }
    }
    var finalOptions = buildOptions(elementType, defaultProps, initData, lifeCycleHandlers, userEventHandlers, elementOption.options, observers, componentPageEventsHandlers);
    log('element options', finalOptions);
    return finalOptions;
}
