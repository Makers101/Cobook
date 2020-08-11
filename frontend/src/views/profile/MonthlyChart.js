import { Bar } from "vue-chartjs";
import { mapState } from 'vuex'
export default {
  extends: Bar,  
  computed: {
    ...mapState('profileStore', ['barData'])
  },
  mounted() {
    this.renderChart(this.barData, 
      {
        scales: {
          yAxes: [{
            ticks: {
              beginAtZero: true
            },
            gridLines: {
              display: true
            }
          }],
          xAxes: [{
            ticks: {
              beginAtZero: true
            },
            gridLines: {
              display: false
            }
          }]
        },
        legend: {
          display: false
        },
        tooltips: {
          enabled: true,
          mode: 'single',
          callbacks: {
            label: function(tooltipItems) {
              return tooltipItems.yLabel + '권';
            }
          }
        },
        responsive: true,
        maintainAspectRatio: false,
        height: 200
      })
  }
};