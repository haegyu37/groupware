import styled from "styled-components";

function SignTableComponent({leftData, rightData, signRefer}) {

    return (
        <SignTableWrapper>
            <table>
                <tbody>
                {leftData.map((data, index) => {
                    return (
                        <tr key={index}>
                            <WriterTitle>{data.title}</WriterTitle>
                            <WriterContents>{data.content}</WriterContents>
                        </tr>
                    )
                })}
                </tbody>
            </table>

            <div>
                <table>
                    <tbody>
                    <tr>
                        <SignLineTitle>결 재</SignLineTitle>
                        {rightData.map((data, index) => {
                            return (
                                <SignLineContent key={index}>
                                    <SignInfo>{data.signTurn}</SignInfo>
                                    <Sign>{data.sign}</Sign>
                                    <SignInfo>{data.signName}</SignInfo>
                                </SignLineContent>
                            )
                        })}
                    </tr>
                    </tbody>
                </table>

                <ReferTable>
                    <ReferTitle>참조</ReferTitle>
                    <ReferContent>
                        {signRefer ? `${signRefer?.name} ${signRefer?.position}` : null}
                    </ReferContent>
                </ReferTable>
            </div>
        </SignTableWrapper>
    )
}

export default SignTableComponent

export const SignTableWrapper = styled.div`
  display: flex;
  margin: 0 140px;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`
export const WriterTitle = styled.td`
  border: 1px solid #000000;
  background: #f4f5f5;
  width: 120px;
  text-align: center;
  font-weight: bold;
  padding: 3px;
`
export const WriterContents = styled.td`
  border: 1px solid #000000;
  width: 150px;
  padding-left: 20px;
  text-align: left;
`
export const SignLineTitle = styled.th`
  width: 30px;
  border: 1px solid #000000;
  background: #f4f5f5;
  text-align: center;
`
export const SignLineContent = styled.td`
  padding: 0;
  margin: 0;
  width: 105px;
  border: 1px solid #424242;
`
export const SignInfo = styled.div`
  background: #f4f5f5;
  width: 105px;
  height: 24px;
  text-align: center;
`
export const Sign = styled.div`
  height: 100px;
  width: 105px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-top: 1px solid #000000;
  border-bottom: 1px solid #000000;

  img {
    width: 65px;
  }
`
export const ReferTable = styled.div`
  border: 1px solid #000000;
  height: 26px;
  margin-top: 20px;
  display: flex;
  flex-direction: row;
`
export const ReferTitle = styled.div`
  border-right: 1px solid #000000;
  width: 135px;
  background: #f4f5f5;
  box-sizing: inherit;
  font-weight: bold;
  text-align: center;
`
export const ReferContent = styled.div`
  padding-left: 20px;
  text-align: left;
`