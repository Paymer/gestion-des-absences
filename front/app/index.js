import angular from 'angular';
import RouteModule from 'angular-route';
import 'bootstrap/dist/css/bootstrap.css';
import { route } from './app.route';

//Services
import connexionService from './connexion/connexion.service';

//Components
import { AccueilComponent } from './accueil/accueil.component';
import ConnexionComponent from './connexion/connexion.component';


//Modules
import menuEmployeModule from './menuEmploye/menuEmploye.module';
import menuAdminModule from './menuAdministrateur/menuAdministrateur.module';


angular.module('app', [RouteModule, menuEmployeModule.name, menuAdminModule.name])
    .value('apiUrl', API_URL)
	.service('connexionService', connexionService)
	
    .component('accueil', AccueilComponent)
	.component('connexionComponent', ConnexionComponent)
	
    .config(route);

