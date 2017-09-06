import angular from 'angular';
import RouteModule from 'angular-route';
import 'bootstrap/dist/css/bootstrap.css';
import uiBootstrap from 'angular-ui-bootstrap'
import { route } from './app.route';

import { AccueilComponent } from './accueil/accueil.component';
import DemandeAbsenceComponent from './absence/demande/absence.component';


import menuEmployeModule from './menuEmploye/menuEmploye.module';


angular.module('app', [RouteModule, menuEmployeModule.name,uiBootstrap])
    .value('apiUrl', API_URL)
    .component('accueil', AccueilComponent)
    .component('demandeAbsence',DemandeAbsenceComponent)
    .config(route);

