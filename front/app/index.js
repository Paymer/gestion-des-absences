
// Basic/Core
import angular from 'angular';
import RouteModule from 'angular-route';
import 'bootstrap/dist/css/bootstrap.css';
import { route } from './app.route';
import ngResource from "angular-resource";
import jssha  from 'jssha';

// Components
import { AccueilComponent } from './accueil/accueil.component';
import ConnexionComponent from './connexion/connexion.component';
import visualisationAbsenceComponent from "./absence/visualisation/visualisationAbsence.component";

// Services
import apiUrls from "./utils/apiUrls.service";
import frontUrls from "./utils/frontUrls.service";
import connexionService from './connexion/connexion.service';
import visualisationAbsenceService from "./absence/visualisation/visualisationAbsence.service"

//Modules

import menuModule from './menu/menu.module';



angular.module('app', [RouteModule, ngResource, menuModule.name])

    .value('apiUrl', API_URL)
    .value('jssha', jssha)
    .constant("apiUrls", apiUrls)

    .value('frontUrl')
    .constant("frontUrls", frontUrls)


	.service('connexionService', connexionService)
    .service('visualisationAbsenceService', visualisationAbsenceService)
	
    .component('accueil', AccueilComponent)
	.component('connexionComponent', ConnexionComponent)
    .component('visualisationAbsenceComponent', visualisationAbsenceComponent)

    .config(route);

