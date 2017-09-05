
// Basic/Core
import angular from 'angular';
import RouteModule from 'angular-route';
import 'bootstrap/dist/css/bootstrap.css';
import { route } from './app.route';
import ngResource from "angular-resource";
import connexionService from './connexion/connexion.service';

// Components
import { AccueilComponent } from './accueil/accueil.component';
import ConnexionComponent from './connexion/connexion.component';
import visualisationAbsenceComponent from "./absence/visualisation/visualisationAbsence.component";

// Services
import visualisationAbsenceService from "./absence/visualisation/visualisationAbsence.service"
import apiUrls from "./utils/apiUrls.service";

//Modules
import menuEmployeModule from './menuEmploye/menuEmploye.module';
import menuAdminModule from './menuAdministrateur/menuAdministrateur.module';
import menuManagerModule from './menuManager/menuManager.module';


angular.module('app', [RouteModule, "ngResource", menuEmployeModule.name, menuAdminModule.name, menuManagerModule.name])

    .value('apiUrl', API_URL)
    .constant("apiUrls", apiUrls)
	.service('connexionService', connexionService)
    .service("visualisationAbsenceService", visualisationAbsenceService)
	
    .component('accueil', AccueilComponent)
	.component('connexionComponent', ConnexionComponent)
    .component("visualisationAbsenceComponent", visualisationAbsenceComponent)

    .config(route);

