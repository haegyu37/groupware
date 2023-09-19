import styled from "styled-components";

export const backgroundColor = {
    "결재대기": "#f8d287",
    "진행중": "#a6e0e8",
    "승인": "#87ea85",
    "반려": "#fb6a76"
}
function StateButton({state}) {

    return (
        <ButtonWrapper>
            <Button background={backgroundColor[state]}>{state}</Button>
        </ButtonWrapper>
    )
}

export default StateButton

const ButtonWrapper = styled.td`
  display: flex;
  justify-content: center;
`
const Button = styled.div`
  min-width: 70px;
  border-radius: 20px;
  text-align: center;
  padding: 4px 8px;
  background: ${props => props.background};
`