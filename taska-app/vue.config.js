// vue.config.js
module.exports = {
  devServer: {
    proxy: {
      "/taska.proto.TaskaService/*": {
        target: "http://localhost:8081/",
        secure: false
      }
    }
  }
};
