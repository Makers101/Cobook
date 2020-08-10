import { Doughnut } from "vue-chartjs";
import { mapState } from 'vuex'
export default {
  extends: Doughnut,
  computed: {
    ...mapState('profileStore', ['pieData'])
  },
  mounted() {
    this.renderChart(this.pieData, {
      borderWidth: "10px",
      hoverBackgroundColor: "red",
      hoverBorderWidth: "10px"
    });
  }
};