import { TElementFunction } from './element.js';
export declare function alipayPage<TProps>(element: TElementFunction<TProps>): Record<string, any>;
export declare function wechatPage<TProps>(element: TElementFunction<TProps>): Record<string, any>;
/**
 * 不暴露 useOnLoad , 使用 useEffect 代替
 */
export declare const useOnShow: import("./hooks.js").THooksFn;
export declare const useOnReady: import("./hooks.js").THooksFn;
export declare const useOnHide: import("./hooks.js").THooksFn;
export declare const useOnPullDownRefresh: import("./hooks.js").THooksFn;
export declare const useOnReachBottom: import("./hooks.js").THooksFn;
export declare const useOnShareAppMessage: import("./hooks.js").THooksFn;
export declare const useOnPageScroll: import("./hooks.js").THooksFn;
export declare const useOnTabItemTap: import("./hooks.js").THooksFn;
export declare const useOnResize: import("./hooks.js").THooksFn;
export declare const useOnTitleClick: import("./hooks.js").THooksFn;
export declare const useOnOptionMenuClick: import("./hooks.js").THooksFn;
export declare const useBeforeTabItemTap: import("./hooks.js").THooksFn;
export declare const useOnKeyboardHeight: import("./hooks.js").THooksFn;
export declare const useOnBack: import("./hooks.js").THooksFn;
export declare const useOnSelectedTabItemTap: import("./hooks.js").THooksFn;
export declare const useBeforeReload: import("./hooks.js").THooksFn;
export * from './export-hooks.js';
export { usePage } from './hooks.js';
