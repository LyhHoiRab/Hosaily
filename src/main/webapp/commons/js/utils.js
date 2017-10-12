var utils = utils || {};

//从源对象中复制不为空undefined的值到目标对象
utils.copyOf = function(source, target){
    if(source !== undefined){
        $.each(target, function(key){
            if(source[key] !== undefined){
                target[key] = source[key];
            }
        });
    }
};