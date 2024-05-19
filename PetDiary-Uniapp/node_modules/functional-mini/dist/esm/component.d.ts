import { TElementFunction } from './element.js';
export interface ComponentOption {
    options?: any;
}
export declare function alipayComponent<TProps extends Record<string, any>>(element: TElementFunction<TProps>, defaultProps?: TProps, componentOption?: ComponentOption): Record<string, any>;
export declare function wechatComponent<TProps extends Record<string, any>>(element: TElementFunction<TProps>, defaultProps?: TProps, componentOption?: ComponentOption): Record<string, any>;
export declare const useAttached: import("./hooks.js").THooksFn;
export declare const useReady: import("./hooks.js").THooksFn;
export declare const useMoved: import("./hooks.js").THooksFn;
export declare const usePageShow: import("./hooks.js").THooksFn;
export declare const usePageHide: import("./hooks.js").THooksFn;
export declare const useDidMount: import("./hooks.js").THooksFn;
export * from './export-hooks.js';
export { useComponent } from './hooks.js';
