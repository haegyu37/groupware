import ToggleButtonGroup from "react-bootstrap/ToggleButtonGroup";
import ToggleButton from "react-bootstrap/ToggleButton";
import styled from "styled-components";

function ButtonGroup({setActiveBtn}) {
    const handleButtonClick = (value) =>{
        setActiveBtn(value)
    }

    return (
        <>
            <DivisionLine/>
            <ButtonContainer>
                <ToggleButtonGroup type="radio" name="options" defaultValue={1}>
                    <SelectButton id="tbg-radio-1" value={1}
                                  onClick={()=>handleButtonClick("all")}
                    >전체</SelectButton>

                    <SelectButton id="tbg-radio-2" value={2}
                                  onClick={()=>handleButtonClick("ongoing")}
                    >진행중</SelectButton>

                    <SelectButton id="tbg-radio-3" value={3}
                                  onClick={()=>handleButtonClick("approved")}
                    >결재완료</SelectButton>

                    <SelectButton id="tbg-radio-4" value={4}
                                  onClick={()=>handleButtonClick("rejected")}
                    >반려</SelectButton>
                </ToggleButtonGroup>
            </ButtonContainer>
            <DivisionLine/>
        </>
    )
}
export default ButtonGroup

const DivisionLine =styled.div`
  border-bottom: 1px solid lightgray;
  width: 600px;
`
const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;
  label {
    border: none;
    border-radius: 0;
    background: rgba(68, 41, 242, 0.11);
    color: #4429f2;
    font-size: 20px;
  }
`
const SelectButton = styled(ToggleButton)`
  --bs-btn-bg: rgba(68, 41, 242, 0.7);
  --bs-btn-active-bg:#4429f2;
  border-bottom: 1px solid #4429f2;
`