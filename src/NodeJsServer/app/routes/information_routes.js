bodyParser = require('body-parser').json();
module.exports = function (app) {
    app.get('/information', (request, response) => {
        const result = {
            "city": "Казань",
            "age": 19,
            "citizenship": "РФ",
            "educ": "КФУ ВШ ИТИС",
            "computer_skills": "Java, python",
            "bad_habits": "Курение",
            "vk": "https://vk.com/posh.ebosh"
        };
        response.send(JSON.stringify(result));
    });
    const  fs = require("fs");
    app.post('/information', bodyParser, (request, response)=>{
        let body = response.body;
        let responseBody = {
            "email": body["email"]
        }
        fs.appendFile("email.txt",JSON.stringify(responseBody), function (error) {
            if(error) throw error;
        })
        response.setHeader("Content-Type", "application/json");
        response.send(JSON.stringify(responseBody));

    });
};
