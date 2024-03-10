import { functionalMiniElement } from './element.js';
import { ETargetPlatform, EElementType } from './types.js';
import { getLifeCycleHooks } from './hooks.js';
import { alipayPageEvents } from './platform.js';
export function alipayPage(element) {
    return functionalMiniElement(element, '', EElementType.page, {}, ETargetPlatform.alipay);
}
export function wechatPage(element) {
    return functionalMiniElement(element, '', EElementType.page, {}, ETargetPlatform.wechat);
}
// 公共生命周期
/**
 * 不暴露 useOnLoad , 使用 useEffect 代替
 */
export var useOnShow = getLifeCycleHooks('onShow');
export var useOnReady = getLifeCycleHooks('onReady');
export var useOnHide = getLifeCycleHooks('onHide');
export var useOnPullDownRefresh = getLifeCycleHooks('onPullDownRefresh');
export var useOnReachBottom = getLifeCycleHooks('onReachBottom');
export var useOnShareAppMessage = getLifeCycleHooks('onShareAppMessage', true);
export var useOnPageScroll = getLifeCycleHooks('onPageScroll');
export var useOnTabItemTap = getLifeCycleHooks('onTabItemTap');
export var useOnResize = getLifeCycleHooks('onResize');
// 支付宝端特有
export var useOnTitleClick = getLifeCycleHooks(alipayPageEvents.onTitleClick, undefined, ETargetPlatform.alipay);
export var useOnOptionMenuClick = getLifeCycleHooks(alipayPageEvents.onOptionMenuClick, undefined, ETargetPlatform.alipay);
export var useBeforeTabItemTap = getLifeCycleHooks(alipayPageEvents.beforeTabItemTap, undefined, ETargetPlatform.alipay);
export var useOnKeyboardHeight = getLifeCycleHooks(alipayPageEvents.onKeyboardHeight, undefined, ETargetPlatform.alipay);
export var useOnBack = getLifeCycleHooks(alipayPageEvents.onBack, undefined, ETargetPlatform.alipay);
export var useOnSelectedTabItemTap = getLifeCycleHooks(alipayPageEvents.onSelectedTabItemTap, undefined, ETargetPlatform.alipay);
export var useBeforeReload = getLifeCycleHooks(alipayPageEvents.beforeReload, undefined, ETargetPlatform.alipay);
export * from './export-hooks.js';
export { usePage } from './hooks.js';
