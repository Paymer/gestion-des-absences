
// Basic/Core
import angular from 'angular';
import RouteModule from 'angular-route';
import 'bootstrap/dist/css/bootstrap.css';
import uiBootstrap from 'angular-ui-bootstrap'
import { route } from './app.route';
import ngResource from "angular-resource";
import connexionService from './connexion/connexion.service';

import { AccueilComponent } from './accueil/accueil.component';
import ConnexionComponent from './connexion/connexion.component';
import visualisationAbsenceComponent from "./absence/visualisation/visualisationAbsence.component";
import DemandeAbsenceComponent from './absence/demande/absence.component';

// Services
import visualisationAbsenceService from "./absence/visualisation/visualisationAbsence.service"
import apiUrls from "./utils/apiUrls.service";
import demandeAbsenceService from "./absence/demande/demandeAbsence.service"
//Modules
import menuEmployeModule from './menuEmploye/menuEmploye.module';
import menuAdminModule from './menuAdministrateur/menuAdministrateur.module';
import menuManagerModule from './menuManager/menuManager.module';


angular.module('app', [RouteModule, ngResource, menuEmployeModule.name, menuAdminModule.name, menuManagerModule.name,uiBootstrap])

    .value('apiUrl', API_URL)
    .constant("apiUrls", apiUrls)
	.service('connexionService', connexionService)
    .service("visualisationAbsenceService", visualisationAbsenceService)
	.service("demandeAbsenceService",demandeAbsenceService)
    .component('accueil', AccueilComponent)
    .component('demandeAbsence',DemandeAbsenceComponent)
    .component('connexionComponent', ConnexionComponent)
    .component("visualisationAbsenceComponent", visualisationAbsenceComponent)
    .config(route);

