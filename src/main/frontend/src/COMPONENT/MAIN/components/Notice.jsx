import styled from "styled-components";

function Notice() {
    return(
        <Wrapper>
            <Title>
                <p>공지사항</p>
            </Title>
            <Contents>
                [공지] 휴가신청서 관련 양식 추가의 건.
            </Contents>
        </Wrapper>
    )
}

export default Notice

const Wrapper = styled.div`
  border: solid 1px rgba(68, 41, 242, 0.6);
  border-radius: 15px;
  width: 500px;
  height: 350px;
  margin: 30px 20px;
`
const Title = styled.div`
  font-size: 24px;
  font-weight: 600;
  border-bottom: solid 1px rgba(68, 41, 242, 0.6);
  background: rgba(125, 121, 242, 0.2);
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
  padding: 5px 20px;
`
const Contents = styled.div`
  color: black;
  padding: 20px 30px;
`