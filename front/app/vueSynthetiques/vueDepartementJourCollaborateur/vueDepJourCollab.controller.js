
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

                                s.absences.forEach(a => {
                                    let partiesDateDebut = a.dateDebut.split("-");
                                    a.anneeDebut = partiesDateDebut[0];
                                    a.moisDebut = partiesDateDebut[1];
                                    a.jourDebut = partiesDateDebut[2];

                                    let partiesDateFin = a.dateFin.split("-");
                                    a.anneeFin = partiesDateFin[0];
                                    a.moisFin = partiesDateFin[1];
                                    a.jourFin = partiesDateFin[2];
                                })

                                s.tableauAbsences = new Array(this.jours);

                                let i;
                                for(i=0 ; i<s.tableauAbsences.length ; i++) {
                                    let jour = new Date(this.anneeCourante, this.moisCourantChiffre, i+1);
                                    s.absences.forEach(a => {
                                        let dayOfWeek = jour.getDay();
                                        let isWeekend = (dayOfWeek == 6) || (dayOfWeek == 0); 
                                        if(moment(jour).isBetween(a.dateDebut, a.dateFin, "day", "[]") && !isWeekend) {
                                            switch(a.type) {
                                                case "CONGES_PAYES": s.tableauAbsences[i] = 'C'; break;
                                                case "RTT": s.tableauAbsences[i] = 'R'; break;
                                                case "CONGES_SANS_SOLDE": s.tableauAbsences[i] = 'S'; break;
                                                case "MISSION": s.tableauAbsences[i] = 'M'; break;
                                                case "RTT_EMPLOYEUR": s.tableauAbsences[i] = 'R'; break;
                                                case "JOUR_FERIE": s.tableauAbsences[i] = 'F'; break;
                                            }
                                        }
                                    })
                                }

                            });
            });
        });

        
    }

    getJours() {
        return new Array(this.jours);
    }

    updateTri() {
        this.triInverse = !this.triInverse;
    }
}