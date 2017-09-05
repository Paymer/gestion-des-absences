import angular from 'angular';
import RouteModule from 'angular-route';
import 'bootstrap/dist/css/bootstrap.css';
import { route } from './app.route';
import { AccueilComponent } from './accueil/accueil.component';



import menuEmployeModule from './menuEmploye/menuEmploye.module';


angular.module('app', [RouteModule, menuEmployeModule.name])
    .value('apiUrl', API_URL)
    .component('accueil', AccueilComponent)
    .config(route);

