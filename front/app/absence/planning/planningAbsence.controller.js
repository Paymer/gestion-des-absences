export default class PlanningAbsenceCtrl{

    constructor(planningAbsenceService,connexionService,$location,$window,calendarConfig){ 
    
        
            this.planningAbsenceService = planningAbsenceService;
            this.events = [];
            this.calendarView = 'month';
            this.viewDate = moment().startOf('month').toDate();
            this.dateDebut = new Date('2017-09-12')
            this.dateFin = new Date('2017-10-23')

            calendarConfig.dateFormatter = 'moment';
            moment.locale('fr'); // change la langue en français
          
            

    }

    cellModifier(cell) {  

       if (cell.date.isBetween(this.dateDebut,this.dateFin,'day','[]')  && cell.inMonth && !cell.isWeekend) {
          cell.cssClass = 'absence';
        }
        cell.label = '-' + cell.label + '-';
      };
  

}