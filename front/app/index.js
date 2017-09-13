
// Basic/Core
import angular from 'angular'
import RouteModule from 'angular-route'
import 'bootstrap/dist/css/bootstrap.css'
import 'angular-bootstrap-calendar/dist/css/angular-bootstrap-calendar.min.css'
import uiBootstrap from 'angular-ui-bootstrap'
import bootstrapCalendar from 'angular-bootstrap-calendar'
import { route } from './app.route'
import ngResource from "angular-resource"
import jssha  from 'jssha'


//Components
import AccueilComponent from './accueil/accueil.component'
import ConnexionComponent from './connexion/connexion.component'
import visualisationAbsenceComponent from "./absence/visualisation/visualisationAbsence.component"
import DemandeAbsenceComponent from './absence/demande/demandeAbsence.component'
import modifAbsenceComponent from './absence/modification/modificationAbsence.component'
import validationAbsenceComponent from './absence/validation/validationAbsence.component'
import planningAbsenceComponent from './absence/planning/planningAbsence.component'
import visualisationFerieComponent from "./ferie/visualisation/visualisationFerie.component"
import vueDepJourCollabComponent from "./vueSynthetiques/vueDepartementJourCollaborateur/vueDepJourCollab.component"

// Services
import apiUrls from "./utils/apiUrls.service"
import demandeAbsenceService from "./absence/demande/demandeAbsence.service"
import frontUrls from "./utils/frontUrls.service"
import connexionService from './connexion/connexion.service'
import visualisationAbsenceService from "./absence/visualisation/visualisationAbsence.service"
import suppressionAbsenceService from "./absence/visualisation/suppression/suppressionAbsence.service"
import modifAbsenceService from "./absence/modification/modificationAbsence.service"
import validationAbsenceService from "./absence/validation/validationAbsence.service"
import planningAbsenceService from "./absence/planning/planningAbsence.service"
import messageService from './accueil/message.service'
import visualisationFerieService from "./ferie/visualisation/visualisationFerie.service"
import vueDepJourCollabService from "./vueSynthetiques/vueDepartementJourCollaborateur/vueDepJourCollab.service"

//Modules
import menuModule from './menu/menu.module'


angular.module('app', [RouteModule, ngResource, menuModule.name, uiBootstrap,bootstrapCalendar])

    .value('jssha', jssha)
    .constant("apiUrls", apiUrls)

    //Services
	.service('connexionService', connexionService)
    .service("visualisationAbsenceService", visualisationAbsenceService)
	.service("demandeAbsenceService",demandeAbsenceService)
    .service("modifAbsenceService",modifAbsenceService)
    .service("validationAbsenceService",validationAbsenceService)
    .service("suppressionAbsenceService", suppressionAbsenceService)
    .service("planningAbsenceService",planningAbsenceService)
	.service("messageService", messageService)
    .service("visualisationFerieService", visualisationFerieService)
    .service("vueDepJourCollabService", vueDepJourCollabService)

    //Components
    .component('accueilComponent', AccueilComponent)
    .component('demandeAbsenceComponent',DemandeAbsenceComponent)
    .component('connexionComponent', ConnexionComponent)
    .component("visualisationAbsenceComponent", visualisationAbsenceComponent)
    .component('modifAbsenceComponent',modifAbsenceComponent)
    .component('validationAbsenceComponent',validationAbsenceComponent)
    .component('planningAbsenceComponent',planningAbsenceComponent)
    .component("visualisationFerieComponent", visualisationFerieComponent)
    .component("vueDepJourCollabComponent", vueDepJourCollabComponent)

    //manage connections and routes
    .config(route)
    .run(['$rootScope', '$location', 'connexionService', function ($rootScope, $location, connexionService) {
    $rootScope.$on('$routeChangeStart', function (event) {
        if (!connexionService.isConnecte()) {
            $location.path('/connexion');
        }
    });
}]);