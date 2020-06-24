module.exports = {
    getNewIdGenerator: () => {
        let count = 0;
        return () => {
            return count++;
        };
    },
};
