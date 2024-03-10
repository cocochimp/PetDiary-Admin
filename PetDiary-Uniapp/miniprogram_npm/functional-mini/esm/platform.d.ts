import { ETargetPlatform, EElementType } from './types.js';
export interface IPlatformConstants {
    name: ETargetPlatform;
    tellIfInThisPlatform: () => boolean;
    pageEvents: string[];
    pageLifeCycleToMount: string;
    pageLifeCycleToUnmount: string;
    componentEvents: string[];
    componentPageEvents: string[];
    blockedProperty: string[];
    componentLifeCycleToMount: string;
    componentLifeCycleToUnmount: string;
    getPropsFromInstance: (instance: any, propNames: string[]) => Record<string, any>;
    buildOptions: (elementType: EElementType, props: any, data: any, lifeCycleHandlers: any, userEventHandlers: any, options: any, observers: any, componentPageLifeCycleHandlers: any) => Record<string, any>;
    supportHandleEventResult: boolean;
}
export interface IWechatProperty {
    [name: string]: {
        type: any;
        value: any;
    };
}
export declare const checkIfPlatformIsLoadCorrectly: (config: IPlatformConstants, target: ETargetPlatform) => boolean;
export declare const commonPageEvents: {
    onLoad: string;
    onShow: string;
    onReady: string;
    onHide: string;
    onPullDownRefresh: string;
    onReachBottom: string;
    onShareAppMessage: string;
    onPageScroll: string;
    onTabItemTap: string;
    onResize: string;
    onUnload: string;
};
export declare const alipayPageEvents: {
    onTitleClick: string;
    onOptionMenuClick: string;
    beforeTabItemTap: string;
    onKeyboardHeight: string;
    onBack: string;
    onSelectedTabItemTap: string;
    beforeReload: string;
};
export declare const commonComponentPageEvents: {
    'page:show': string;
    'page:hide': string;
};
export type ComponentPageEvents = keyof typeof commonComponentPageEvents;
/**
 * @see https://developers.weixin.qq.com/miniprogram/dev/framework/custom-component/lifetimes.html
 */
export declare const wechatComponentPageEvents: Record<ComponentPageEvents, string>;
/**

 * @see https://opendocs.alipay.com/mini/framework/page-detail#events
 */
export declare const alipayComponentPageEvents: Record<ComponentPageEvents, string>;
export declare const commonComponentEvents: Record<string, string>;
export declare const alipayComponentEvents: {
    onInit: string;
    didMount: string;
    didUpdate: string;
    deriveDataFromProps: string;
    didUnmount: string;
};
export declare const wechatComponentEvents: {
    error: string;
};
export declare const blockedProperty: string[];
export declare const platformConfig: Record<ETargetPlatform, IPlatformConstants>;
