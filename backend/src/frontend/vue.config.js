module.exports = {
    devServer :{
        port : 3000,
        proxy:{
            '/api':{
                target: 'http://localhost:9095',
                ws:true,
                changeOrigin:true
            }
        }
    }
}