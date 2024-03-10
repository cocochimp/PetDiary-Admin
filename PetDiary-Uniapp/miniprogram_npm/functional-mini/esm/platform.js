var _a;
import { ETargetPlatform, EElementType } from './types.js';
import { mergeObjectKeys } from './utils.js';
var ifInAlipay = function () {
    //@ts-expect-error
    return typeof my !== 'undefined';
};
var ifInWeChat = function () {
    //@ts-expect-error
    return typeof wx !== 'undefined';
};
export var checkIfPlatformIsLoadCorrectly = function (config, target) {
    if (config.name !== target) {
        var errMsg = "\u671F\u671B\u7684\u8FD0\u884C\u5E73\u53F0\u4E3A ".concat(target, "\uFF0C\u4F46\u662F\u5F53\u524D\u5E73\u53F0\u914D\u7F6E\u4E3A ").concat(config.name, "\uFF0C\u8BF7\u68C0\u67E5\u662F\u5426\u52A0\u8F7D\u4E86\u6B63\u786E\u7684\u5E73\u53F0\u914D\u7F6E");
        throw new Error(errMsg);
        // 这里的判断方法可能不准，有反馈了再改
    }
    else if (!config.tellIfInThisPlatform()) {
        var errMsg = "\u671F\u671B\u7684\u8FD0\u884C\u5E73\u53F0\u4E3A ".concat(target, "\uFF0C\u4F46\u662F\u5F53\u524D\u8FD0\u884C\u65F6\u73AF\u5883\u4E0D\u662F ").concat(target, "\uFF0C\u8BF7\u68C0\u67E5\u662F\u5426\u52A0\u8F7D\u4E86\u6B63\u786E\u7684\u5E73\u53F0\u914D\u7F6E");
        throw new Error(errMsg);
    }
    return true;
};
// 方便 ts 类型推导
export var commonPageEvents = {
    onLoad: 'onLoad',
    onShow: 'onShow',
    onReady: 'onReady',
    onHide: 'onHide',
    onPullDownRefresh: 'onPullDownRefresh',
    onReachBottom: 'onReachBottom',
    onShareAppMessage: 'onShareAppMessage',
    onPageScroll: 'onPageScroll',
    onTabItemTap: 'onTabItemTap',
    onResize: 'onResize',
    onUnload: 'onUnload',
};
export var alipayPageEvents = {
    onTitleClick: 'onTitleClick',
    onOptionMenuClick: 'onOptionMenuClick',
    beforeTabItemTap: 'beforeTabItemTap',
    onKeyboardHeight: 'onKeyboardHeight',
    onBack: 'onBack',
    onSelectedTabItemTap: 'onSelectedTabItemTap',
    beforeReload: 'beforeReload',
};
export var commonComponentPageEvents = {
    'page:show': 'page:show',
    'page:hide': 'page:hide',
};
/**
 * @see https://developers.weixin.qq.com/miniprogram/dev/framework/custom-component/lifetimes.html
 */
export var wechatComponentPageEvents = {
    'page:hide': 'hide',
    'page:show': 'show',
};
/**

 * @see https://opendocs.alipay.com/mini/framework/page-detail#events
 */
export var alipayComponentPageEvents = {
    'page:hide': 'onHide',
    'page:show': 'onShow',
};
export var commonComponentEvents = {
    created: 'created',
    attached: 'attached',
    ready: 'ready',
    moved: 'moved',
    detached: 'detached',
};
export var alipayComponentEvents = {
    onInit: 'onInit',
    didMount: 'didMount',
    didUpdate: 'didUpdate',
    deriveDataFromProps: 'deriveDataFromProps',
    didUnmount: 'didUnmount',
};
export var wechatComponentEvents = {
    error: 'error',
};
// 保留字，不允许开发者注册使用
export var blockedProperty = ['mixins', 'methods', 'observers', 'pageEvents'];
export var platformConfig = (_a = {},
    _a[ETargetPlatform.alipay] = {
        name: ETargetPlatform.alipay,
        supportHandleEventResult: true,
        tellIfInThisPlatform: ifInAlipay,
        pageEvents: mergeObjectKeys(commonPageEvents, alipayPageEvents),
        pageLifeCycleToMount: commonPageEvents.onLoad,
        pageLifeCycleToUnmount: commonPageEvents.onUnload,
        componentEvents: mergeObjectKeys(commonComponentEvents, alipayComponentEvents),
        componentPageEvents: Object.keys(commonComponentPageEvents),
        componentLifeCycleToMount: alipayComponentEvents.onInit,
        componentLifeCycleToUnmount: alipayComponentEvents.didUnmount,
        blockedProperty: blockedProperty,
        getPropsFromInstance: function (instance) {
            return instance.props;
        },
        buildOptions: function (elementType, props, data, lifeCycleHandlers, userEventHandlers, options, _observers, componentPageLifeCycleHandlers) {
            if (options === void 0) { options = null; }
            if (elementType === EElementType.page) {
                return Object.assign({
                    data: data,
                    options: options,
                }, lifeCycleHandlers, userEventHandlers);
            }
            else {
                /**
                 * 参考这里
                 * https://opendocs.alipay.com/mini/framework/component-lifecycle
                 */
                var alipayLifeCycle_1 = {};
                var commonLifetime_1 = {};
                Object.keys(lifeCycleHandlers).forEach(function (key) {
                    if (commonComponentEvents[key]) {
                        commonLifetime_1[key] = lifeCycleHandlers[key];
                    }
                    else {
                        alipayLifeCycle_1[key] = lifeCycleHandlers[key];
                    }
                });
                var rootEvents_1 = {};
                Object.keys(componentPageLifeCycleHandlers).map(function (p) {
                    rootEvents_1[alipayComponentPageEvents[p]] =
                        componentPageLifeCycleHandlers[p];
                });
                return Object.assign({
                    rootEvents: rootEvents_1,
                    props: props,
                    data: data,
                    options: Object.assign({
                        lifetimes: true,
                    }, options || {}),
                    lifetimes: commonLifetime_1,
                }, alipayLifeCycle_1, { methods: userEventHandlers });
            }
        },
    },
    _a[ETargetPlatform.wechat] = {
        name: ETargetPlatform.wechat,
        componentPageEvents: Object.keys(commonComponentPageEvents),
        tellIfInThisPlatform: ifInWeChat,
        supportHandleEventResult: false,
        pageEvents: Object.keys(commonPageEvents),
        pageLifeCycleToMount: commonPageEvents.onLoad,
        pageLifeCycleToUnmount: commonPageEvents.onUnload,
        componentEvents: mergeObjectKeys(commonComponentEvents, wechatComponentEvents),
        componentLifeCycleToMount: commonComponentEvents.attached,
        componentLifeCycleToUnmount: commonComponentEvents.detached,
        blockedProperty: blockedProperty,
        getPropsFromInstance: function (instance, propNames) {
            var newProps = {};
            for (var _i = 0, propNames_1 = propNames; _i < propNames_1.length; _i++) {
                var propName = propNames_1[_i];
                //@ts-expect-error
                newProps[propName] = instance.data[propName];
            }
            return newProps;
        },
        buildOptions: function (elementType, props, data, lifeCycleHandlers, userEventHandlers, options, observers, componentPageLifeCycleHandlers) {
            if (options === void 0) { options = {}; }
            if (elementType === EElementType.page) {
                return Object.assign({
                    data: data,
                    options: options,
                }, lifeCycleHandlers, userEventHandlers);
            }
            else {
                var defaultProps = props || {};
                var properties = {};
                for (var key in defaultProps) {
                    if (!Object.prototype.hasOwnProperty.call(defaultProps, key))
                        continue;
                    var value = defaultProps[key];
                    var targetType = void 0;
                    if (typeof value === 'string') {
                        targetType = String;
                    }
                    else if (typeof value === 'number') {
                        targetType = Number;
                    }
                    else if (typeof value === 'boolean') {
                        targetType = Boolean;
                    }
                    else if (Array.isArray(value)) {
                        targetType = Array;
                    }
                    else if (typeof value === 'object') {
                        if (value === null) {
                            targetType = null;
                        }
                        else {
                            targetType = Object;
                        }
                    }
                    else {
                        throw new Error("\u4E0D\u652F\u6301\u7684 properties \u7C7B\u578B: ".concat(key, " - ").concat(typeof value));
                    }
                    properties[key] = {
                        type: targetType,
                        value: value,
                    };
                }
                var pageLifetimes_1 = {};
                Object.keys(componentPageLifeCycleHandlers).map(function (p) {
                    pageLifetimes_1[wechatComponentPageEvents[p]] =
                        componentPageLifeCycleHandlers[p];
                });
                return {
                    pageLifetimes: pageLifetimes_1,
                    properties: properties,
                    data: data,
                    options: options,
                    observers: observers,
                    lifetimes: lifeCycleHandlers || {},
                    methods: userEventHandlers || {},
                };
            }
        },
    },
    _a);
