import ngRoute from 'angular-route'

// Components
import menuManagerComponent from "./menuManager.component"


const menuManagerModule = angular
    .module('menuMangerModule', [ngRoute])
    .component('menuManagerComponent', menuManagerComponent)

export default menuManagerModule