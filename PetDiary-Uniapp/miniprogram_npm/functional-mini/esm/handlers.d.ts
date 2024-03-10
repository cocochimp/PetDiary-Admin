export type TFunctionImpl = (...args: any[]) => any;
interface IImplInfo {
    fn: TFunctionImpl;
    bindContext: any;
    off?: () => void;
}
export default class HandlersController {
    elementTag: string;
    locked: boolean;
    executionInProgress: boolean;
    executionQueue: Array<() => void>;
    handlerNames: string[];
    handlerImpl: Record<string, IImplInfo[]>;
    constructor(elementTag?: string);
    addHandler(name: string, bindContext?: any, impl?: TFunctionImpl, disableMultiImpl?: boolean): () => void;
    lockHandlerNames(): void;
    resetAllImpl(): void;
    getHandlersByName(name: string, context: any): IImplInfo[];
    callHandlers(name: string, context: any, args: any[]): any;
    getHandlersImplProxy(): {};
    private filterContext;
}
export {};
