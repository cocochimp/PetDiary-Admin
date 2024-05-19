import { IElementContext } from './hooks.js';
import { ETargetPlatform, EElementType } from './types.js';
export interface IAppxOptions {
    data: Record<string, any>;
    options: Record<string, string>;
    methods?: Record<string, (args: any) => void>;
    [handlerName: string]: any;
}
export type TElementFunction<TProps> = (props: TProps) => {
    [axmlKey: string]: any;
};
export interface IInstanceMap {
    [id: string]: {
        appxContext: IElementContext;
        appxId: string;
        elementFn: any;
        elementInstance?: any;
        pendingProps: any;
        unmounted: boolean;
        propKeys: string[];
    };
}
export declare function flushReactTree(elementMap: IInstanceMap): import("preact").VNode<import("preact").ClassAttributes<HTMLElement> | null>;
export interface ElementOption {
    options?: any;
}
export declare function functionalMiniElement<TProps>(element: TElementFunction<TProps>, displayName: string | undefined, elementType: EElementType, defaultProps?: TProps, _targetPlatform?: ETargetPlatform, elementOption?: ElementOption): Record<string, any>;
