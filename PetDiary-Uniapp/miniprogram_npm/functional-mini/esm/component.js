import { functionalMiniElement } from './element.js';
import { getLifeCycleHooks } from './hooks.js';
import { alipayComponentEvents, commonComponentEvents, commonComponentPageEvents, } from './platform.js';
import { EElementType, ETargetPlatform } from './types.js';
export function alipayComponent(element, defaultProps, componentOption) {
    return functionalMiniElement(element, '', EElementType.component, defaultProps, ETargetPlatform.alipay, componentOption);
}
export function wechatComponent(element, defaultProps, componentOption) {
    return functionalMiniElement(element, '', EElementType.component, defaultProps, ETargetPlatform.wechat, componentOption);
}
export var useAttached = getLifeCycleHooks(commonComponentEvents.attached);
export var useReady = getLifeCycleHooks(commonComponentEvents.ready);
export var useMoved = getLifeCycleHooks(commonComponentEvents.moved);
export var usePageShow = getLifeCycleHooks(commonComponentPageEvents['page:show']);
export var usePageHide = getLifeCycleHooks(commonComponentPageEvents['page:hide']);
export var useDidMount = getLifeCycleHooks(alipayComponentEvents.didMount, undefined, ETargetPlatform.alipay);
export * from './export-hooks.js';
export { useComponent } from './hooks.js';
