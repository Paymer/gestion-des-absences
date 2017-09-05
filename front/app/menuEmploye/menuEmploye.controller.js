export default class MenuEmployeCtrl{
    constructor(logService){
        this.logService = logService
        this.logo = {title: "Logo", url: "#"}
    }
    
    getConnectionStatus(){
        return this.logService.isConnecte()
    }
}

MenuEmployeCtrl.$inject = ['connexionService']