
// Basic/Core
import angular from 'angular';
import RouteModule from 'angular-route';
import 'bootstrap/dist/css/bootstrap.css';
import uiBootstrap from 'angular-ui-bootstrap'
import { route } from './app.route';
import ngResource from "angular-resource";
import jssha  from 'jssha';

import { AccueilComponent } from './accueil/accueil.component';
import ConnexionComponent from './connexion/connexion.component';
import visualisationAbsenceComponent from "./absence/visualisation/visualisationAbsence.component";
import DemandeAbsenceComponent from './absence/demande/demandeAbsence.component';

// Services
import apiUrls from "./utils/apiUrls.service";
import demandeAbsenceService from "./absence/demande/demandeAbsence.service"
import frontUrls from "./utils/frontUrls.service";
import connexionService from './connexion/connexion.service';
import visualisationAbsenceService from "./absence/visualisation/visualisationAbsence.service"

//Modules
import menuModule from './menu/menu.module';



angular.module('app', [RouteModule, ngResource, menuModule.name,uiBootstrap])

    .value('apiUrl', API_URL)
    .value('jssha', jssha)
    .constant("apiUrls", apiUrls)

    .value('frontUrl')
    .constant("frontUrls", frontUrls)


	.service('connexionService', connexionService)
    .service("visualisationAbsenceService", visualisationAbsenceService)
	.service("demandeAbsenceService",demandeAbsenceService)

    .component('accueil', AccueilComponent)
    .component('demandeAbsence',DemandeAbsenceComponent)
    .component('connexionComponent', ConnexionComponent)
    .component("visualisationAbsenceComponent", visualisationAbsenceComponent)
    .config(route);

