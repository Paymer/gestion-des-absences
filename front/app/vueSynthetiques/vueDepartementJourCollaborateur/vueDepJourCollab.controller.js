
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
        this.annees = [2016, 2017, 2018, 2019, 2020]; // Les récupérer en fonction des absences existantes ?

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
        this.moisCourantChiffre = this.vueDepJourCollabService.getMoisEnChiffre(this.moisCourantLettres);

        this.subalternes.forEach(s => {
            this.vueDepJourCollabService.ajoutTableauAbsences(s, this.jours, this.anneeCourante, this.moisCourantChiffre)
        })

    }

    getJours() {
        return new Array(this.jours);
    }

    updateTri() {
        this.triInverse = !this.triInverse;
    }

    nomExcel() {
        return this.moisCourantLettres + "-" + this.anneeCourante + "-" + this.departementCourant
    }

    exportExcel() {
        var i, j;
        var csv = "";
        
        var table = document.getElementById("tableVue");
        
        var table_headings = table.children[0].children[0].children;
        var table_body_rows = table.children[1].children;
        
        var heading;
        var headingsArray = [];
        for(i = 0; i < table_headings.length; i++) {
          heading = table_headings[i];
          headingsArray.push('"' + heading.textContent.trim() + '"');
        }
        
        csv += headingsArray.join(',') + "\n";
        
        var row;
        var columns;
        var column;
        var columnsArray;
        for(i = 0; i < table_body_rows.length; i++) {
          row = table_body_rows[i];
          columns = row.children;
          columnsArray = [];
          for(j = 0; j < columns.length; j++) {
              var column = columns[j];
              columnsArray.push('"' + column.textContent.trim() + '"');
          }
          csv += columnsArray.join(',') + "\n";
        }
        
          this.download(this.nomExcel() + ".xls", csv);
    }


    download(filename, text) {
        var pom = document.createElement('a');
        pom.setAttribute('href', 'data:text/csv;charset=utf-8,' + encodeURIComponent(text));
        pom.setAttribute('download', filename);
    
        if (document.createEvent) {
            var event = document.createEvent('MouseEvents');
            event.initEvent('click', true, true);
            pom.dispatchEvent(event);
        }
        else {
            pom.click();
        }
    }

}