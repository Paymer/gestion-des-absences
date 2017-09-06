import ngRoute from 'angular-route'

// Components
import menuComponent from "./menu.component"
import frontUrls from "./../utils/frontUrls.service";


const menuModule = angular
    .module('menuModule', [ngRoute])
    .component('menuComponent', menuComponent)

    .value('frontUrl')
    .constant("frontUrls", frontUrls)

export default menuModule