module.exports = {
    randomInt: function(min,max) {
        return Math.floor(min + Math.random() * (max + 1 - min));
    },

    randomStr: function(length) {
        return Math.random().toString(36).substr(2, length).replace(/\d+/g, "b");
    }
};
