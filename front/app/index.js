
// Basic/Core
import angular from 'angular';
import RouteModule from 'angular-route';
import 'bootstrap/dist/css/bootstrap.css';
import { route } from './app.route';

// Module
import menuEmployeModule from './menuEmploye/menuEmploye.module';

// Component
import { AccueilComponent } from './accueil/accueil.component';
import visualisationAbsenceComponent from "./absence/visualisation/visualisationAbsence.component"

// Service
import visualisationAbsenceService from "./absence/visualisation/visualisationAbsence.service"



angular.module('app', [RouteModule, menuEmployeModule.name])
    .value('apiUrl', API_URL)
    .component('accueil', AccueilComponent)
    .component("visualisationAbsenceComponent", visualisationAbsenceComponent)
    .config(route);

