
export default class VueDepJourCollabController {
    constructor() {
        
    }

    $onInit() {
        this.mois = ["Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"];
        this.moisEnNombre = [1,2,3,4,5,6,7,8,9,10,11,12];
        this.annees = [2016, 2017, 2018]; // Les récupérer en fonction des absences existantes ?
        /* TODO
            prendre le mois courant, jour courant ?, années courantes, définir un département de base
            
        */
    }
}