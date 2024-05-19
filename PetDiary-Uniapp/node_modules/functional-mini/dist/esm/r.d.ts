import { preactHooks, preact, serverRender } from './3rd-party/preact.js';
import { act } from './3rd-party/preact-test-utils.js';
declare const createContext: typeof preact.createContext;
declare const useCallback: typeof preactHooks.useCallback, useEffect: typeof preactHooks.useEffect, useMemo: typeof preactHooks.useMemo, useState: typeof preactHooks.useState, useContext: typeof preactHooks.useContext, useLayoutEffect: typeof preactHooks.useLayoutEffect, useRef: typeof preactHooks.useRef, useReducer: typeof preactHooks.useReducer;
declare const createElement: typeof preact.h;
export { act, useRef, createElement, createContext, serverRender, useCallback, useEffect, useMemo, useState, useContext, useLayoutEffect, useReducer, };
export type VNode = preact.VNode;
export declare const mountElement: (element: any) => {
    unmount(): void;
    update(newElement: Element): void;
    toString(): string;
    dom: Element;
};
