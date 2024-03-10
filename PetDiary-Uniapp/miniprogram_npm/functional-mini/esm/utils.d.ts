export declare const setLogSwitch: (on: boolean) => void;
export declare const log: (...args: any[]) => void;
export declare const error: (...args: any[]) => void;
export declare const time: (label?: string) => void;
export declare const timeEnd: (label?: string) => void;
export declare const shallowCompare: (obj1: any, obj2: any, skipKey?: string[]) => boolean;
export declare enum EComponent2Status {
    VALID = "VALID",
    INVALID = "INVALID",
    UNKNOWN = "UNKNOWN"
}
export declare const getComponent2Status: () => EComponent2Status;
export declare const updateComponent2Status: (status?: EComponent2Status) => void;
export declare function getIdFromAppxInstance(appxInstance: any): any;
export declare const instanceKeyPropNames = "_functional_instance_prop_names_";
export declare function mergeObjectKeys(...objects: Record<string, unknown>[]): string[];
