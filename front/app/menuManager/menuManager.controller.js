export default class MenuManagerCtrl{
    constructor(logService){
        this.logService = logService
        this.logo = {title: "Logo", url: "#"}
    }
    
    getConnectionStatus(){
        return this.logService.isConnecte()
    }
}

MenuManagerCtrl.$inject = ['connexionService']