export default class MenuCtrl{
    constructor(logService, frontUrls, $location){
        this.logService = logService
        this.$location = $location
        this.logo = {title: "Logo", url: "#"}
        this.frontUrls = frontUrls
        this.grade = (this.logService.getGrade() === 'Manager')
        
    }
    
   

    deconnecter(){
        this.logService.deconnecter();
        this.rediger('xx')
    }

    isConnected (){
         return this.logService.isConnecte();
    }


    rediger (a){
        console.log(this.logService.isConnecte())
        console.log(this.frontUrls.connexion)
        if (this.logService.isConnecte()){
            switch (a){
                case 'absence':
                this.$location.path(this.frontUrls.absence)
                break;
                case 'planningDemande':
                this.$location.path(this.frontUrls.planningDemande)
                break;
                case 'rapport':
                this.$location.path(this.frontUrls.rapport)
                break;
                case 'ferie':
                this.$location.path(this.frontUrls.ferie)
                break;
                default:
                this.$location.path(this.frontUrls.accueil)
                break;
            }
            
          
        }
        else{
            this.$location.path(this.frontUrls.connexion)
         
        }
        
    }


}



MenuCtrl.$inject = ['connexionService', 'frontUrls', '$location']