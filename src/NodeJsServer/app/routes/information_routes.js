module.exports = function (app) {
    app.get('/information', (request, response) => {
        const result = [{
            "city": "Казань",
            "age": 19,
            "citizenship" : "РФ",
            "educ" : "КФУ ВШ ИТИС"
        },
            {
                "computer_skills": "Java, python",
                "bad_habits": "Курение",
                "vk" : "https://vk.com/posh.ebosh"
            }];
        response.send(JSON.stringify(result));
    });
};
