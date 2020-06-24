module.exports = {
    randomInt: function(min,max) {
        return Math.floor(min + Math.random() * (max + 1 - min));
    },
};
