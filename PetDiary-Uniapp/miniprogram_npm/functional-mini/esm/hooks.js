var __spreadArray = (this && this.__spreadArray) || function (to, from, pack) {
    if (pack || arguments.length === 2) for (var i = 0, l = from.length, ar; i < l; i++) {
        if (ar || !(i in from)) {
            if (!ar) ar = Array.prototype.slice.call(from, 0, i);
            ar[i] = from[i];
        }
    }
    return to.concat(ar || Array.prototype.slice.call(from));
};
import { useEffect, createContext, useContext, useRef, useCallback, } from './r.js';
import { instanceKeyPropNames } from './utils.js';
import { checkIfPlatformIsLoadCorrectly, } from './platform.js';
//@ts-expect-error
function generateEventHookName(eventName) {
    return "use".concat(eventName[0].toUpperCase()).concat(eventName.slice(1));
}
export var reactContext = createContext(null);
// --------------------------
function useAppxContext() {
    var appxInstanceContext = useContext(reactContext);
    if (!appxInstanceContext) {
        throw new Error('请不要在组件内调用 hooks');
    }
    return appxInstanceContext;
}
function useEventCall(name, handler, disableMultiImpl) {
    var realHandler = useStableCallback(handler);
    var appxInstanceContext = useAppxContext();
    if (appxInstanceContext.ifServerRender) {
        // 虚拟渲染时，注册空实现
        appxInstanceContext.handlersController.addHandler(name, {}, function () { }, false);
    }
    useEffect(function () {
        var off = appxInstanceContext.handlersController.addHandler(name, appxInstanceContext.instance, 
        //@ts-expect-error
        function () {
            var args = [];
            for (var _i = 0; _i < arguments.length; _i++) {
                args[_i] = arguments[_i];
            }
            return realHandler.apply(undefined, args);
        }, disableMultiImpl);
        return off;
    }, []);
}
export function useStableCallback(callback) {
    var fnRef = useRef();
    fnRef.current = callback;
    var memoFn = useCallback((function () {
        var _a;
        var args = [];
        for (var _i = 0; _i < arguments.length; _i++) {
            args[_i] = arguments[_i];
        }
        return (_a = fnRef.current) === null || _a === void 0 ? void 0 : _a.call.apply(_a, __spreadArray([fnRef], args, false));
    }), []);
    return memoFn;
}
// 注册和更新 handler，注意只能更新第一次注册过的 handler 实现，不允许变更数量和 key
export function useEvent(name, handler, 
/**
 *
 */
depsOrOptions) {
    var appxInstanceContext = useAppxContext();
    var platformConfig = appxInstanceContext.platformConfig, instance = appxInstanceContext.instance;
    var pageEvents = platformConfig.pageEvents, componentEvents = platformConfig.componentEvents, blockedProperty = platformConfig.blockedProperty;
    if (pageEvents.indexOf(name) >= 0 || componentEvents.indexOf(name) >= 0) {
        throw new Error("\u5C0F\u7A0B\u5E8F ".concat(name, " \u662F\u751F\u547D\u5468\u671F\u76F8\u5173\u7684\u4FDD\u7559\u65B9\u6CD5\uFF0C\u4E0D\u5141\u8BB8\u4F7F\u7528\u6B64\u79CD\u65B9\u6CD5\u6CE8\u518C\uFF0C\u8BF7\u4F7F\u7528\u5BF9\u5E94\u7684 hooks: ").concat(generateEventHookName(name)));
    }
    else if (blockedProperty.indexOf(name) >= 0) {
        throw new Error("\u4E0D\u5141\u8BB8\u6CE8\u518C\u540D\u4E3A ".concat(name, " \u7684\u4E8B\u4EF6\u5904\u7406\u51FD\u6570\uFF0C\u8FD9\u662F\u5C0F\u7A0B\u5E8F\u7684\u4FDD\u7559\u5C5E\u6027\uFF0C\u8BF7\u6362\u4E00\u4E2A\u540D\u79F0"));
    }
    if (Array.isArray(depsOrOptions)) {
        console.warn("useEventCall ".concat(name, ": hooks \u7684 deps \u5DF2\u5E9F\u5F03\uFF0C\u65E0\u9700\u586B\u5199\u3002"));
    }
    var putMethodOnData = !Array.isArray(depsOrOptions) &&
        (depsOrOptions === null || depsOrOptions === void 0 ? void 0 : depsOrOptions.handleResult) &&
        !platformConfig.supportHandleEventResult;
    // 对于同一个 name ,此变量永久不变，所以可以用 if else 写 hooks
    if (putMethodOnData) {
        var stableHandler_1 = useStableCallback(handler);
        useEffect(function () {
            var _a;
            if (instance.properties &&
                typeof instance.properties[name] !== 'undefined') {
                throw new Error("\u4E8B\u4EF6 ".concat(name, " \u6CE8\u518C\u5931\u8D25\uFF0C\u5728 handleResult \u5F00\u542F\u540E\uFF0C\u4E8B\u4EF6\u4E0D\u80FD\u540C\u65F6\u5728 properties \u4E0E useEvent \u4E2D\u5B9A\u4E49\u3002"));
            }
            instance.setData((_a = {},
                _a[name] = stableHandler_1,
                _a));
        }, []);
    }
    else {
        useEventCall(name, handler, true);
    }
}
export function getLifeCycleHooks(eventName, disableMultiImpl, specifyPlatform) {
    if (disableMultiImpl === void 0) { disableMultiImpl = false; }
    return function (handler, 
    /**
     * @deprecated 无需填写依赖
     */
    deps) {
        var appxInstanceContext = useAppxContext();
        if (specifyPlatform) {
            var platformConfig = appxInstanceContext.platformConfig;
            checkIfPlatformIsLoadCorrectly(platformConfig, specifyPlatform);
        }
        useEventCall(eventName, handler, disableMultiImpl);
    };
}
export function useSyncMiniData(data) {
    if (data === void 0) { data = {}; }
    var appxInstanceContext = useAppxContext();
    // const propKeys = appxInstanceContext
    if (!appxInstanceContext.instance) {
        throw new Error('cannot get appx instance, failed to set data');
    }
    var lastDataRef = useRef();
    if (appxInstanceContext.ifServerRender) {
        appxInstanceContext.instance.setData(data);
    }
    var debugLog = appxInstanceContext.debugLog || function () { };
    if (typeof data !== 'object')
        throw new Error("\u51FD\u6570\u8FD4\u56DE\u7684\u6570\u636E\u5FC5\u987B\u662F\u4E00\u4E2A\u5BF9\u8C61\uFF0C\u6536\u5230\u4E86 ".concat(typeof data));
    // 这里是一个每次都要跑的 passive effect
    var instance = appxInstanceContext.instance;
    var propNames = instance[instanceKeyPropNames] || []; // 微信的 data 里包含了 props，要手动踢掉
    var pendingData = {};
    var previousData = instance.data || {};
    // 比对一下，只 set 不同的部分
    for (var key in data) {
        if (!Object.prototype.hasOwnProperty.call(data, key))
            continue;
        //@ts-expect-error
        if (typeof data[key] === 'function')
            throw new Error("".concat(key, " - \u4E0D\u5141\u8BB8\u4F20\u5165\u51FD\u6570\u7C7B\u578B\u7684\u6570\u636E")); // 暂不支持，有需求再说
        //@ts-expect-error
        if (!previousData[key] || previousData[key] !== data[key]) {
            //@ts-expect-error
            pendingData[key] = data[key];
        }
        if (typeof previousData[key] === 'function') {
            throw new Error("".concat(key, " - \u7981\u6B62\u4FEE\u6539 data \u4E0A\u5DF2\u7ECF\u5B58\u5728\u7684\u51FD\u6570"));
        }
    }
    // 如果之前同步过 data，但是这次没有传入，就把之前的值设置成 null
    // 这里用的是 lastDataRef 而不是 previousData，是因为 previousData 里可能有用户通过 this.data[key] 直接修改的值
    if (lastDataRef.current) {
        // 缺少某些 key，就设置成 null
        for (var _i = 0, _a = Object.keys(lastDataRef.current); _i < _a.length; _i++) {
            var key = _a[_i];
            if (propNames.indexOf(key) >= 0)
                continue;
            if (!Object.prototype.hasOwnProperty.call(data, key)) {
                //@ts-expect-error
                pendingData[key] = null;
            }
        }
    }
    if (Object.keys(pendingData).length > 0) {
        debugLog('calling setData', pendingData);
        instance.setData(pendingData);
    }
    lastDataRef.current = data;
}
function useMiniInstance() {
    var appxInstanceContext = useAppxContext();
    return appxInstanceContext.instance;
}
export var usePage = useMiniInstance;
export var useComponent = useMiniInstance;
