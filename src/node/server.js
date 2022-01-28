const express = require('express')
const app = express()
const port = 3000

app.get('/', (req, res) => {
    setInterval(function() {
        res.status(200).end('');
    },parseInt(req.query.delay) * 1000);
})

app.listen(port, () => {
    console.log(`Example app listening on port ${port}`)
})