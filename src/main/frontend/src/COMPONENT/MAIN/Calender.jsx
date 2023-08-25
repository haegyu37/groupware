import {useState} from "react";
import styles from "./Calender.module.css"
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

function Calender() {
    const [value, onChange] = useState(new Date());

    return(
        <div className={styles.wrapper}>
            <Calendar className={styles.calendar}
                      onChange={onChange}
                      vlaue={value}
                      next2Label={null}
                      prev2Label={null}
                      formatDay={(locale, date) =>
                          new Date(date).toLocaleDateString("en-us", {
                              day: "2-digit",
                          })
                      }
            />
        </div>
    )
}

export default Calender