
// Basic/Core
import angular from 'angular';
import RouteModule from 'angular-route';
import 'bootstrap/dist/css/bootstrap.css';
import uiBootstrap from 'angular-ui-bootstrap'
import { route } from './app.route';
import ngResource from "angular-resource";
import jssha  from 'jssha';

//Components
import { AccueilComponent } from './accueil/accueil.component';
import ConnexionComponent from './connexion/connexion.component';
import visualisationAbsenceComponent from "./absence/visualisation/visualisationAbsence.component";
import DemandeAbsenceComponent from './absence/demande/demandeAbsence.component';
import modifAbsenceComponent from './absence/modification/modificationAbsence.component';

// Services
import apiUrls from "./utils/apiUrls.service";
import demandeAbsenceService from "./absence/demande/demandeAbsence.service"
import frontUrls from "./utils/frontUrls.service";
import connexionService from './connexion/connexion.service';
import visualisationAbsenceService from "./absence/visualisation/visualisationAbsence.service"
import modifAbsenceService from "./absence/modification/modificationAbsence.service"

//Modules
import menuModule from './menu/menu.module';



angular.module('app', [RouteModule, ngResource, menuModule.name,uiBootstrap])

    .value('apiUrl', API_URL)
    .value('jssha', jssha)
    .constant("apiUrls", apiUrls)

    .value('frontUrl')
    .constant("frontUrls", frontUrls)

    //Services
	.service('connexionService', connexionService)
    .service("visualisationAbsenceService", visualisationAbsenceService)
	.service("demandeAbsenceService",demandeAbsenceService)
    .service("modifAbsenceService",modifAbsenceService)

    //Components
    .component('accueil', AccueilComponent)
    .component('demandeAbsence',DemandeAbsenceComponent)
    .component('connexionComponent', ConnexionComponent)
    .component("visualisationAbsenceComponent", visualisationAbsenceComponent)
    .component('modifAbsenceComponent',modifAbsenceComponent)
    //manage connections and routes
    .config(route)
    .run(['$rootScope', '$location', 'connexionService', function ($rootScope, $location, connexionService) {
    $rootScope.$on('$routeChangeStart', function (event) {
        if (!connexionService.isConnecte()) {
            console.log('DENY');
            $location.path('/connexion');
        }
        else {
            console.log('ALLOW');
           
        }
    });
}]);