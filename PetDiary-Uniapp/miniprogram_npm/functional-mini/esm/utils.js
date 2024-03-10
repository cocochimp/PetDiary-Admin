var logSwitch = false;
export var setLogSwitch = function (on) {
    logSwitch = on;
};
//@ts-expect-error
export var log = function () {
    var args = [];
    for (var _i = 0; _i < arguments.length; _i++) {
        args[_i] = arguments[_i];
    }
    if (!logSwitch)
        return;
    console.log.apply(console, args);
};
//@ts-expect-error
export var error = function () {
    var args = [];
    for (var _i = 0; _i < arguments.length; _i++) {
        args[_i] = arguments[_i];
    }
    console.error.apply(console, args);
};
export var time = function (label) {
    if (!logSwitch)
        return;
    console.time(label);
};
export var timeEnd = function (label) {
    if (!logSwitch)
        return;
    console.timeEnd(label);
};
//@ts-expect-error
export var shallowCompare = function (obj1, obj2, skipKey) {
    if (obj1 === obj2)
        return true;
    if (typeof obj1 !== typeof obj2)
        return false;
    if (typeof obj1 !== 'object' || typeof obj2 !== 'object')
        return false;
    if (obj1 === null || obj2 === null)
        return false;
    if (Array.isArray(obj1) || Array.isArray(obj2))
        return false;
    if (Object.keys(obj1).length !== Object.keys(obj2).length)
        return false;
    for (var key in obj1) {
        if (obj1[key] !== obj2[key] && (!skipKey || skipKey.indexOf(key) === -1)) {
            return false;
        }
    }
    return true;
};
// 这么做是为了测试方便，隔离一些全局变量
export var EComponent2Status;
(function (EComponent2Status) {
    EComponent2Status["VALID"] = "VALID";
    EComponent2Status["INVALID"] = "INVALID";
    EComponent2Status["UNKNOWN"] = "UNKNOWN";
})(EComponent2Status || (EComponent2Status = {}));
var component2Status = EComponent2Status.UNKNOWN;
export var getComponent2Status = function () {
    return component2Status;
};
export var updateComponent2Status = function (status) {
    if (status) {
        component2Status = status;
        //@ts-expect-error
    }
    else if (typeof my !== 'undefined') {
        //@ts-expect-error
        component2Status = my.canIUse('component2')
            ? EComponent2Status.VALID
            : EComponent2Status.INVALID;
    }
};
var incrementalId = 0;
var instanceKeyId = '_functional_instance_id_';
//@ts-expect-error
export function getIdFromAppxInstance(appxInstance) {
    var _a;
    if (!appxInstance)
        throw new Error('appxInstance param is falsy');
    var id;
    // 支付宝端
    if (appxInstance.$id) {
        id = "".concat(appxInstance.$id, "-").concat(((_a = appxInstance.$page) === null || _a === void 0 ? void 0 : _a.$viewId) || '', "-").concat(appxInstance.is || 'page'); /* 页面 */
    }
    else if (appxInstance[instanceKeyId]) {
        id = appxInstance[instanceKeyId];
    }
    else {
        id = "instance-".concat(incrementalId++);
        appxInstance[instanceKeyId] = id;
    }
    if (!id)
        throw new Error('failed to identify appxInstance.$id');
    return id;
}
export var instanceKeyPropNames = '_functional_instance_prop_names_';
export function mergeObjectKeys() {
    var objects = [];
    for (var _i = 0; _i < arguments.length; _i++) {
        objects[_i] = arguments[_i];
    }
    var res = [];
    for (var _a = 0, objects_1 = objects; _a < objects_1.length; _a++) {
        var iterator = objects_1[_a];
        res = res.concat(Object.keys(iterator));
    }
    return res;
}
