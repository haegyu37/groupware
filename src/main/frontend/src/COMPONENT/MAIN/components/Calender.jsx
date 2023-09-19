import {useState} from "react";
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import styled from "styled-components";

function Calender() {
    const [value, onChange] = useState(new Date());

    return (
        <Wrapper>
            <StyledCalendar
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
        </Wrapper>
    )
}

export default Calender

const Wrapper = styled.div`
  margin: 20px 20px;
`
const StyledCalendar = styled(Calendar)`
  border-radius: 10px;
`