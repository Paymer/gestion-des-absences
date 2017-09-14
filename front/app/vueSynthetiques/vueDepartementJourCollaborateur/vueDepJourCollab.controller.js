
export default class VueDepJourCollabController {
    constructor(vueDepJourCollabService) {
        this.vueDepJourCollabService = vueDepJourCollabService;
    }

    $onInit() {
        this.order = "nom";
        this.triInverse = false;

        this.moisEnLettres = this.vueDepJourCollabService.getMoisEnLettres();
        this.moisEnNombre = this.vueDepJourCollabService.getMoisEnNombre();
        this.moisCourantChiffre = new Date().getMonth(); // Renvoie le mois actuel (entre 0 et 11)
        this.moisCourantLettres = this.moisEnLettres[this.moisCourantChiffre];
        this.anneeCourante = new Date().getFullYear(); // Renvoie l'année actuelle
        this.annees = [2016, 2017, 2018]; // Les récupérer en fonction des absences existantes ?

        this.jours = this.vueDepJourCollabService.nombreJoursDuMois(this.moisCourantChiffre+1, this.anneeCourante);

        this.vueDepJourCollabService.getDepartements().then(departements => {
            this.departements = departements.map(d => d.libelle);
            this.departementCourant = this.departements[0];
        });

        this.vueDepJourCollabService.getSubalternes().then(subalternes => {
            this.subalternes = subalternes;

            // Pour chaque subalterne, récupère ses absences respectives
            this.subalternes.forEach(s => {
                this.vueDepJourCollabService.getAbsencesParMatricule(s.matricule)
                            .then(a => {
                                s.absences = a;
                                this.vueDepJourCollabService.ajoutTableauAbsences(s, this.jours, this.anneeCourante, this.moisCourantChiffre);
                            });
            });
        });

        
    }

    changement() {
        console.log(this.departementCourant, this.anneeCourante, this.moisCourantLettres);

        this.moisCourantChiffre = this.vueDepJourCollabService.getMoisEnChiffre(this.moisCourantLettres);
        console.log(this.moisCourantChiffre);
        console.log(this.subalternes);

        this.vueDepJourCollabService.ajoutTableauAbsences(this.subalternes, this.jours, this.anneeCourante, this.moisCourantChiffre)
    }

    getJours() {
        return new Array(this.jours);
    }

    updateTri() {
        this.triInverse = !this.triInverse;
    }
}