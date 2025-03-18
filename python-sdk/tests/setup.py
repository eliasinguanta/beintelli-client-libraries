import asyncio

from tests.credentials import Credential
from src.beintelli_platform_python_sdk_eliasinguanta.user import User

future_login: asyncio.Future[User] = None

async def get_authenticated_user() -> asyncio.Future[User]:
    """logs in with correct credentials"""
    global future_login

    if future_login is not None:
        return future_login
    else:
        future_login = asyncio.Future()
        student = User()
        try:
            await (await student.login(Credential.USERNAME.value, Credential.PASSWORD.value))
            future_login.set_result(student)
        except Exception as e:
            future_login.set_exception(str(e))
        return future_login