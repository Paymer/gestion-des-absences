
// Basic/Core
import angular from 'angular'
import RouteModule from 'angular-route'
import 'bootstrap/dist/css/bootstrap.css'
import uiBootstrap from 'angular-ui-bootstrap'
import { route } from './app.route'
import ngResource from "angular-resource"
import jssha  from 'jssha'
import 'chart.js/dist/Chart.min.js'
import 'angular-chart.js/dist/angular-chart.js'

//Components
import { AccueilComponent } from './accueil/accueil.component'
import ConnexionComponent from './connexion/connexion.component'
import visualisationAbsenceComponent from "./absence/visualisation/visualisationAbsence.component"
import DemandeAbsenceComponent from './absence/demande/demandeAbsence.component'
import modifAbsenceComponent from './absence/modification/modificationAbsence.component'
import validationAbsenceComponent from './absence/validation/validationAbsence.component'
import histogrammeComponent from './rapport/histogramme/histogramme.component'

// Services
import apiUrls from "./utils/apiUrls.service"
import demandeAbsenceService from "./absence/demande/demandeAbsence.service"
import frontUrls from "./utils/frontUrls.service"
import connexionService from './connexion/connexion.service'
import visualisationAbsenceService from "./absence/visualisation/visualisationAbsence.service"
import suppressionAbsenceService from "./absence/visualisation/suppression/suppressionAbsence.service"
import modifAbsenceService from "./absence/modification/modificationAbsence.service"
import validationAbsenceService from "./absence/validation/validationAbsence.service"
import histogrammeService from './rapport/histogramme/histogramme.service'

//Modules
import menuModule from './menu/menu.module'


angular.module('app', [RouteModule, ngResource, menuModule.name, uiBootstrap, 'chart.js'])

    .value('jssha', jssha)
    .constant("apiUrls", apiUrls)

    //Services
	.service('connexionService', connexionService)
    .service("visualisationAbsenceService", visualisationAbsenceService)
	.service("demandeAbsenceService",demandeAbsenceService)
    .service("modifAbsenceService",modifAbsenceService)
    .service("validationAbsenceService",validationAbsenceService)
    .service("suppressionAbsenceService", suppressionAbsenceService)
    .service("histogrammeService", histogrammeService)

    //Components
    .component('accueilComponent', AccueilComponent)
    .component('demandeAbsenceComponent',DemandeAbsenceComponent)
    .component('connexionComponent', ConnexionComponent)
    .component("visualisationAbsenceComponent", visualisationAbsenceComponent)
    .component('modifAbsenceComponent',modifAbsenceComponent)
    .component('validationAbsenceComponent',validationAbsenceComponent)
    .component('histogrammeComponent',histogrammeComponent)

    //manage connections and routes
    .config(route)
    .run(['$rootScope', '$location', 'connexionService', function ($rootScope, $location, connexionService) {
    $rootScope.$on('$routeChangeStart', function (event) {
        if (!connexionService.isConnecte()) {
            $location.path('/connexion');
        }
    });
}]);