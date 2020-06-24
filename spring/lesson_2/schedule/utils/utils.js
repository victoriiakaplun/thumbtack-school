'use strict';

module.exports = {
    randomInt: function(min,max) {
        return Math.floor(min + Math.random() * (max + 1 - min));
    },

    randomStr: function(length) {
        return Math.random().toString(36).substr(2, length).replace(/\d+/g, "b");
    },

    dayOfWeekAsString: function (dayIndex) {
        return ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"][dayIndex];
    },

    askQuestion: async function (question, rl) {
        return new Promise((resolve, reject) => {
            rl.question(question, (str) => {
                if (!str) {
                    reject();
                }
                resolve(str);
            });
        });
    }
};
