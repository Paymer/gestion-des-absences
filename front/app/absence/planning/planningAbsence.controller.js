export default class PlanningAbsenceCtrl{

    constructor(planningAbsenceService,connexionService,$location,$window,calendarConfig){ 
    
            this.connexionService= connexionService;
            this.planningAbsenceService = planningAbsenceService;
            this.events = [];
            this.calendarView = 'month'; // c'est la vu pour les mois
            this.viewDate = moment().startOf('month').toDate();
            this.viewChangeEnabled = true;

            calendarConfig.dateFormatter = 'moment';
            moment.locale('fr'); // change la langue en français
            
          }
          
          $onInit(){
            this.connexionService.getCongesPayesEtRttFromBase()
            .then(result => {
                let res = result.data
                this.congesPayes = res.congesPayes;
                this.rtt = res.rtt;

                this.connexionService.setCongesPayes(this.congesPayes);
                this.connexionService.setRtt(this.rtt);
            });

            this.ferier = this.planningAbsenceService.findAllFerier();
          
          }
          viewChangeClicked(date, nextView) {
            
            return this.viewChangeEnabled;
          };   
          
      cellModifier(cell) {  
            
            this.planningAbsenceService.findAll().then(absences => {
              this.absences = absences;
              absences.forEach( abs => {
                
                
                if (cell.date.isBetween(abs.dateDebut,abs.dateFin,'day','[]')  && cell.inMonth && !cell.isWeekend) {
                  
                  cell.cssClass = 'absence';
                  cell.label = cell.label+"-"+abs.type;
                }else{
                  
                  cell.label =  cell.label;
                }
              
              });
              
            });
            this.planningAbsenceService.findAllFerier().then(ferier =>{
          
          
              ferier.forEach( f =>{
                
                if (cell.date.isSame(f.date)  && cell.inMonth && !cell.isWeekend) {
                  
                  cell.cssClass = 'jourferie';
                  cell.label = f.date.substring(8,f.date.length)+"-" + f.type;
                }else{
                  
                  cell.label =  cell.label;
                }
                
                
                  
                
                
                })
           })
            
          };
  

}