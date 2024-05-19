import { preactHooks, preact, serverRender } from './3rd-party/preact.js';
import { virtualDocument } from './3rd-party/virtual-document.js';
import { act } from './3rd-party/preact-test-utils.js';
var createContext = preact.createContext, h = preact.h, render = preact.render, options = preact.options;
var useCallback = preactHooks.useCallback, useEffect = preactHooks.useEffect, useMemo = preactHooks.useMemo, useState = preactHooks.useState, useContext = preactHooks.useContext, useLayoutEffect = preactHooks.useLayoutEffect, useRef = preactHooks.useRef, useReducer = preactHooks.useReducer;
var createElement = h;
options.requestAnimationFrame = function (cb) {
    return setTimeout(cb);
};
options.debounceRendering = function (cb) {
    return setTimeout(cb);
};
export { act, useRef, createElement, createContext, serverRender, useCallback, useEffect, useMemo, useState, useContext, useLayoutEffect, useReducer, };
export var mountElement = function (element) {
    var entryDom = virtualDocument.createElement('div');
    render(element, entryDom);
    return {
        unmount: function () {
            render(h('div', {}, 'unmounted'), entryDom);
        },
        update: function (newElement) {
            render(newElement, entryDom);
        },
        toString: function () {
            return entryDom.innerHTML;
        },
        dom: entryDom,
    };
};
